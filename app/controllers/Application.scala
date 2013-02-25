package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Task

object Application extends Controller {
  
  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
  	Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
  	taskForm.bindFromRequest.fold(
  		errors => BadRequest(views.html.index(Task.all, errors)),
  		task => {
  			Task.create(task.label, task.description)
  			Redirect(routes.Application.tasks)
  		}
  	)
  }

  def deleteTask(id: Long) = Action {
  	Task.delete(id)
  	Redirect(routes.Application.tasks)
  }

  val taskForm = Form(
    mapping(
    	"id" -> ignored(123L),
	"label" -> nonEmptyText,
	"description" -> text
  )(Task.apply)(Task.unapply))
}
