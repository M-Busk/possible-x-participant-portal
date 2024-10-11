rootProject.name = "possible-x-participant-portal"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("libs.versions.toml"))
    }
  }
}

include("backend", "frontend")