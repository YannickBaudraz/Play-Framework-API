package api.v1.service

import api.v1.model.BaseModel

import scala.concurrent.Future

trait ModelService {
  def list(): Future[Seq[BaseModel]]
  def get(id: Int): Future[BaseModel]
  def create(model: BaseModel): Future[BaseModel]
  def update(model: BaseModel): Future[BaseModel]
  def delete(id: Int): Future[Unit]
}
