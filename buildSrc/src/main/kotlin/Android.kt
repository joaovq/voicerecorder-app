import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.android() {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
}

fun DependencyHandlerScope.compose(isNeedNavigation: Boolean = true) {
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:${Versions.composeBomVersion}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    androidTestImplementation(platform("androidx.compose:compose-bom:${Versions.composeBomVersion}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    if (isNeedNavigation) {
        implementation("androidx.navigation:navigation-compose:${Versions.composeNavVersion}")
    }
}

fun DependencyHandlerScope.test() {
    testImplementation("junit:junit:${Versions.junitVersion}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidJUnitVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espressoCore}")
}



fun DependencyHandlerScope.room() {
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    annotationProcessor("androidx.room:room-compiler:${Versions.roomVersion}")
    kapt("androidx.room:room-compiler:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
}

fun DependencyHandlerScope.hilt() {
    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.hiltComposeVersion}")
}