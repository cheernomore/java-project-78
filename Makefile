checkstyle:
	cd app && ./gradlew checkstyleTest && cd ..
	cd app && ./gradlew checkstyleMain && cd ..

test:
	cd app && ./gradlew test && cd ..

report:
	cd app && ./gradlew jacocoTestReport && cd ..