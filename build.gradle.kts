group = "com.vl.expressionparser"
version = "0.1"

plugins {
    `java-library`
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.vl.expressionparser"
            artifactId = "expression-parser"
            version = "0.1"

            from(components["java"])
        }
    }
}