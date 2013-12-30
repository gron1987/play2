package models

/**
 * Created with IntelliJ IDEA.
 * User: vlogvinskiy
 * Date: 12/30/13
 * Time: 11:11 AM

 */
sealed trait Permission
case object Administrator extends Permission
case object NormalUser extends Permission

object Permission {

  def valueOf(value: String): Permission = value match {
    case "Administrator" => Administrator
    case "NormalUser"    => NormalUser
    case _ => throw new IllegalArgumentException()
  }

}
