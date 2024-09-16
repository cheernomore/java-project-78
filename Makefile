.DEFAULT_GOAL := build-run

run-dist:
		cd app && build/install/app/bin/app

install:
		cd app && ./gradlew clean install

run:
		cd app && ./gradlew run

report:
		cd app && ./gradlew jacocoTestReport

lint:
		cd app && ./gradlew checkstyleMain checkstyleTest

test:
		cd app && ./gradlew test
build:
		cd app && ./gradlew build
