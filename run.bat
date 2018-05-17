md bin
md lib
dir /s /B *.java > sources
call gradlew build
call gradlew copyRuntimeLibs
javac -d bin -cp "lib/*;bin" @sources
java -cp "bin;lib/*;src/main/resources" pl.agh.edu.ethereumreservations.EthereumApplication
rem java -cp "out/production/classes;lib/*" pl.agh.edu.ethereumreservations.EthereumApplication
