package org.mj.dw.healthcheck

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import com.typesafe.scalalogging.LazyLogging

class DWServiceHealthCheck(appName: String) extends HealthCheck with LazyLogging {
  override def check(): Result = Result.healthy(appName + " is healthy")
}
