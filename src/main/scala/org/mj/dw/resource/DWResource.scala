package org.mj.dw.resource

import javax.ws.rs.{GET, Path}

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.annotation.Timed
import org.joda.time.DateTime

@Path("/dw")
class DWResource(name: String, metrics: MetricRegistry) {
  @GET
  @Path("/greet")
  @Timed
  def greet = s"Hey! $name, its ${DateTime.now}"
}
