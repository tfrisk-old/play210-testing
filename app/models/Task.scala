package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String, description: String)

object Task {
	def all(): List[Task] = DB.withConnection { implicit c =>
		SQL("select * from task").as(task *)
	}

	def create(label: String, description: String) {
		DB.withConnection { implicit c =>
			SQL("insert into task (label,description) values ({label}, {description})").on(
				'label -> label, 'description -> description).executeUpdate()
		}
	}

	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from task where id = {id}").on(
				'id -> id).executeUpdate()
		}
	}
	
	val task = {
		get[Long]("id") ~
		get[String]("label") ~
		get[String]("description") map {
			case id~label~description => Task(id, label, description)
		}
  	}
}
