package org.mj.dw.app

import com.datasift.dropwizard.scala.{ScalaApplication, ScalaBundle}
import com.typesafe.scalalogging.LazyLogging
import io.dropwizard.setup.{Bootstrap, Environment}
import org.joda.time.DateTime
import org.mj.dw.healthcheck.DWServiceHealthCheck
import org.mj.dw.resource.DWResource

object DWApp extends ScalaApplication[DWAppConfiguration] with LazyLogging {
  override def init(bootstrap: Bootstrap[DWAppConfiguration]): Unit = {
    logger.info(s"Init ${this.getClass}")
  }

  def run(conf: DWAppConfiguration, env: Environment): Unit = {
    logger.info(s"Starting ${conf.appName} @ ${DateTime.now()} with $conf")
    // App resources
    val resource = new DWResource(conf.appName, env.metrics)

    env.jersey.register(new ScalaBundle)
    env.jersey.register(resource)

    // App health checks
    env.healthChecks.register("", new DWServiceHealthCheck(""))
  }
}