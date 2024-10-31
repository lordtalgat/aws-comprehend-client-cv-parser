

name := "aws-text"

version := "0.1"

scalaVersion := "2.13.8"

val awsSdkVersion = "1.12.228"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-textract" % awsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-comprehend" % awsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-sns" % awsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % awsSdkVersion
)
