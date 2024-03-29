import play.api._
import scalikejdbc._, SQLInterpolation._
import models._

/**
 * Created with IntelliJ IDEA.
 * User: vlogvinskiy
 * Date: 12/30/13
 * Time: 10:39 AM

 */
object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

    // ad-hoc session provider on the REPL
    implicit val session = AutoSession

    // table creation, you can run DDL by using #execute as same as JDBC
    sql"""
create table members (
  id serial not null primary key,
  name varchar(64),
  created_at timestamp not null
);
CREATE TABLE account (
    id         integer NOT NULL PRIMARY KEY,
    email      text NOT NULL UNIQUE,
    password   text NOT NULL,
    name       text NOT NULL,
    permission text NOT NULL
);
""".execute.apply()

    // insert initial data
    Seq("Alice", "Bob", "Chris") foreach { name =>
      sql"insert into members (name, created_at) values (${name}, current_timestamp)".update.apply()
    }

    // for now, retrieves all data as Map value
    val entities: List[Map[String, Any]] = sql"select * from members".map(_.toMap).list.apply()

    // defines entity object and extractor
    import org.joda.time._
    object Member extends SQLSyntaxSupport[Member] {
      override val tableName = "members"
      def apply(rs: WrappedResultSet) = new Member(
        rs.long("id"), rs.stringOpt("name"), rs.dateTime("created_at"))
    }
    case class Member(id: Long, name: Option[String], createdAt: DateTime)

    // find all members
    val members: List[Member] = sql"select * from members".map(rs => Member(rs)).list.apply()

    println(members)

    if (Account.findAll.isEmpty) {
      Seq(
        Account(1, "alice@example.com", "secret", "Alice", Administrator),
        Account(2, "bob@example.com",   "secret", "Bob",   NormalUser),
        Account(3, "chris@example.com", "secret", "Chris", NormalUser)
      ) foreach Account.create
    }
  }

}
