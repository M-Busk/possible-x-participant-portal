import com.github.gradle.node.npm.task.NpxTask

plugins {
  id("com.github.node-gradle.node") version "7.0.2"
}

// Configure the plugin to download and use specific Node version
node {
  download = true
  version = "20.16.0"
}

// Register NpmTask that will do what "npm run build" command does.
tasks.register<NpxTask>("npmBuild") {
  description = "Builds the Angular WebApp."
  group = "Application"
  command.set("npm")
  args.set(listOf( "run", "build"))
}
