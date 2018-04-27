package org.mj.dw.app.resource

import javax.ws.rs._
import javax.ws.rs.core.MediaType

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.util.Try

@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
@Path("/dw")
class DWResource(appName: String, metrics: MetricRegistry) extends LazyLogging {

  import DWResource._

  @GET
  @Path("error")
  def errorQP = new BadRequestException(s"Invalid $appName Request $suffixMessage errorQP")

  @GET
  @Path("option")
  @Timed
  def optionQP(@QueryParam("qp") qp: Option[String]): String = s"hi from $qp $suffixMessage optionQP"

  @GET
  @Path("string")
  @Timed
  def stringQP(@QueryParam("qp") qp: String): String = s"hi for $qp $suffixMessage stringQP"

  @GET
  @Path("list")
  @Timed
  def stringListQP(@QueryParam("qp") qpl: Seq[String]): Seq[String] = {
    if (qpl.nonEmpty)
      for (qp <- qpl) yield s"hi $qp $suffixMessage stringListQP"
    else
      List(s"Hey! from $appName $suffixMessage stringListQP")
  }

  @GET
  @Path("try")
  def tryA(@QueryParam("qp") qp: Option[String]): Try[String] = Try {
    s"${qp.get} $suffixMessage"
  }

  @POST
  @Path("post")
  def insertRow(row: (BigDecimal, Option[String])): Any = {
    logger.info(s"Inserted $row $suffixMessage")
    row
  }
}


object DWResource {
  private def suffixMessage = s", its ${DateTime.now}"
}