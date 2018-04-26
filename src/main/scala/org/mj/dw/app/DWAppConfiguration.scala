package org.mj.dw.app

import io.dropwizard.Configuration
import org.hibernate.validator.constraints.NotEmpty

class DWAppConfiguration extends Configuration {
  @NotEmpty val appName: String = "dropwizard_service"
}
