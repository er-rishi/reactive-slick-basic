/**
 * @author Rishi
 */
import slick.lifted.{ ProvenShape, ForeignKeyQuery }
import slick.driver.PostgresDriver.api._

case class Player(id: Int, name: String, teamId: Int)

class PlayerMapping(tag: Tag) extends Table[Player](tag, "players") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def teamId = column[Int]("teamID", O.NotNull)

  def * = (id, name, teamId) <> (Player.tupled, Player.unapply)

  def dept = foreignKey("TEAM_FK", teamId, TableQuery[CricketTeamMapping])(_.id)

}
