package org.mj.dw.resource

import javax.ws.rs._

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

@Path("/dw")
@Produces(Array("application/json"))
class DWResource(appName: String, metrics: MetricRegistry) extends LazyLogging {

  import DWResource._

  @GET
  @Path("greet")
  @Timed
  def greet(@QueryParam("qp") qpl: Seq[String]): String = s"Greetings! for $qpl $suffixMessage"

  @GET
  @Path("list")
  @Timed
  def greetList(@QueryParam("qp") qpl: Seq[String]): Seq[String] = {
    if (qpl.nonEmpty)
      for (qp <- qpl) yield s"Hello $qp $suffixMessage"
    else
      List(s"Hey! from $appName, its ${DateTime.now}")
  }

  @GET
  @Path("error")
  def error = new BadRequestException(s"Invalid $appName Request $suffixMessage")

  @GET
  @Path("option")
  @Timed
  def optional(@QueryParam("qp") qp: Option[String]): String = s"Hey! from $qp $suffixMessage"

//  @GET
//  @Path("ol")
//  @Timed
//  def optionalList(@QueryParam("qp") qpl: Option[Seq[String]]): Unit = {
//    logger.info(s"qp= $qpl")
//  }

}


object DWResource {
  private def suffixMessage = s", its ${DateTime.now}"
}