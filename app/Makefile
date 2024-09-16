.DEFAULT_GOAL := build-run

run-dist:
		build/install/app/bin/app

install:
		./gradlew clean install

run:
		./gradlew run

report:
		./gradlew jacocoTestReport

lint:
		./gradlew checkstyleMain checkstyleTest

test:
		./gradlew clean test
build:
		./gradlew build
