

/**
 * @author Rishi
 */
import slick.driver.PostgresDriver.api._
import slick.backend.DatabasePublisher
import scala.concurrent.{ Future, Await }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeoutException

object DAO {
  def db = Database.forConfig("db")
  val players = TableQuery[PlayerMapping]
  val teams = TableQuery[CricketTeamMapping]

  def createSchema = {

    val schema: DBIO[Unit] = DBIO.seq(
      (players.schema ++ teams.schema).create,
      teams ++= Seq(
        CricketTeam(1, "CSK"),
        CricketTeam(2, "RR"),
        CricketTeam(3, "RCB"),
        CricketTeam(4, "DD")),
      players ++= Seq(
        Player(1, "Dhoni", 1),
        Player(2, "Watson", 2),
        Player(3, "Raina", 1)))

    try { db.run(schema) } finally db.close
  }

  def count = try { db.run(players.result) } finally db.close

  def insertPlayer(player: Player) = try { db.run(players += player) } finally db.close

  def insertTeam(team: CricketTeam) = try { db.run(teams += team) } finally db.close

  def updatePlayer(id: Int, player: Player) = try { db.run(players.filter(_.id === id).update(player)) } finally db.close

  def updatePlayer(id: Int, teamId: Int) = try { db.run(players.filter(_.id === id).map(_.teamId).update(teamId)) } finally { db.close }

  def delete(id: Int) = try { db.run(players.filter(_.id === id).delete) } finally { db.close }

  def findPlayerByTeamId(id: Int) = try { db.run(players.filter(_.teamId === id).result) } finally { db.close }

  def findTeamNamebyPlayerId(id: Int) = try {
    val query = for {
      p <- players.filter(_.id === id)
      t <- teams.filter(_.id === p.teamId)
    } yield (t.name)
    db.run(query.result.head)
  } finally { db.close }

  def leftJoin(id: Int) = try {
    val query = for {
      (p, t) <- players joinLeft teams on (_.teamId === _.id)
    } yield (p, t)
    db.run(query.result)
  } finally { db.close }

  def join(id: Int) = try {
    val query = for {
      (p, t) <- players joinRight teams on (_.teamId === _.id)
    } yield (p, t)
    db.run(query.result)
  } finally { db.close }

  def fullJoin(id: Int) = try {
    val query = for {
      (p, t) <- players joinFull teams on (_.teamId === _.id)
    } yield (p, t)
    db.run(query.result)
  } finally { db.close }

  def main(args: Array[String]) {

    // Await.result(insertTeam(CricketTeam(4, "DD")), Duration.Inf)
    Await.result(join(2) map { x => x.map { case (y, z) => println("result " + "----" + y + "---------" + z) } }, Duration.Inf)

  }
}