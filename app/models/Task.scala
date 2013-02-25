package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, label: String, description: String, assignee: String)

object Task {
	def all(): List[Task] = DB.withConnection { implicit c =>
		SQL("select * from task").as(task *)
	}

	def create(label: String, description: String, assignee: String) {
		DB.withConnection { implicit c =>
			SQL("insert into task (label,description, assignee) values ({label}, {description}, {assignee})").on(
				'label -> label,
				'description -> description,
				'assignee -> assignee).executeUpdate()
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
		get[String]("description") ~
		get[String]("assignee") map {
			case id~label~description~assignee => Task(id, label, description, assignee)
		}
  	}
}
