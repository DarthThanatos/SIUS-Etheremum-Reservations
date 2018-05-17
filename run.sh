mkdir bin
mkdir lib
find -name "*.java" > sources
gradle build
gradle copyRuntimeLibs
javac -d bin -cp "lib/*:bin" @sources
java -cp "bin:lib/*" pl.agh.edu.ethereumreservations.EthereumApplication
# java -cp "out/production/classes:lib/*" pl.agh.edu.ethereumreservations.EthereumApplication
