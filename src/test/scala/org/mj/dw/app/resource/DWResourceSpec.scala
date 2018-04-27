package org.mj.dw.app.resource

import java.io.File

import com.datasift.dropwizard.scala.test.{ApplicationTest, BeforeAndAfterAllMulti}
import com.google.common.io.Resources
import com.typesafe.scalalogging.LazyLogging
import org.mj.dw.app.DWApp
import org.scalatest.FlatSpec

class DWResourceSpec extends FlatSpec with BeforeAndAfterAllMulti with LazyLogging {
  private lazy val client = app.newClient("test")
  private val testConfigFile = "dw_service_test.yml"
  private val app = ApplicationTest(this, new File(Resources.getResource(testConfigFile).toURI).getAbsolutePath) {
    DWApp
  }

  private def request(target: String) = for {
    client <- client
    server <- app.server
  } yield {
    client.target(server.getURI.resolve(target))
  }

  "testOptionEndpoint" should "greet with configured names" in {
    //val expected = app.configuration.map(conf => conf.names.map(conf.greeting.getOrElse("%s").format(_)))
    val result = request("/dw/option").map(_.request().get(classOf[String]))
    logger.error(s"testOptionEndpoint: $result")
    assert(result.get.contains(" None "))
  }

  "testListEndpoint" should "greet with configured names" in {
    //val expected = app.configuration.map(conf => conf.names.map(conf.greeting.getOrElse("%s").format(_)))
    val result = request("/dw/list").map(_.request().get(classOf[List[String]]))
    logger.error(s"testListEndpoint: $result")
    assert(result.get.size == 1)
  }

  "testListEndpointWithQP" should "greet with configured names" in {
    //val expected = app.configuration.map(conf => conf.names.map(conf.greeting.getOrElse("%s").format(_)))
    val result = request("/dw/list?qp=1&qp=2&qp=3&qp=4").map(_.request().get(classOf[List[String]]))
    logger.error(s"testListEndpoint: $result")
    assert(result.get.size == 4)
  }

}
