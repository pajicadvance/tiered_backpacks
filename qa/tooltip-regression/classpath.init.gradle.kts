import org.gradle.api.tasks.SourceSetContainer

gradle.projectsEvaluated {
    val target = rootProject.findProject(":26.3-fabric") ?: return@projectsEvaluated
    target.tasks.register("writeTooltipRegressionClasspath") {
        dependsOn(target.tasks.named("classes"))
        doLast {
            val main = target.extensions.getByType<SourceSetContainer>().getByName("main")
            val destination = File(System.getProperty("java.io.tmpdir"), "tiered-backpacks-regression.classpath")
            destination.writeText((main.output + main.compileClasspath + main.runtimeClasspath).asPath)
            println(destination)
        }
    }
}
