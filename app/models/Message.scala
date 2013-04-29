package models

import org.joda.time.LocalDate

/**
 * User: mauricio
 * Date: 4/26/13
 * Time: 11:43 PM
 */
case class Message ( id : Option[Long], content : String, moment : LocalDate = LocalDate.now() )