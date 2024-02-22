import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandlerScope.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}
fun DependencyHandlerScope.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandlerScope.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandlerScope.annotationProcessor(dependency: String) {
    add("annotationProcessor", dependency)
}

fun DependencyHandlerScope.kapt(dependency: String) {
    add("kapt", dependency)
}