#

all: test

test:
	./gradlew test

clean:
	@rm -rf build
