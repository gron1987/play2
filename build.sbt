name := "play2"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"               % "[1.7,)",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "[1.7,)",
  "com.h2database"  %  "h2"                        % "[1.3,)",
  "ch.qos.logback"  %  "logback-classic"           % "[1.0,)"
)

libraryDependencies ++= Seq(
  "jp.t2v" %% "play2-auth"      % "0.11.0"
)

libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

play.Project.playScalaSettings
