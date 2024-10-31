Mini app to run AWS Amazon Comprehend Service to parse CV

private val awsCreds = new BasicAWSCredentials("username", "password")
insert correct password and username

 val arnNumber = "000000000000" //insert correct arn Number
 val dataAccessRoleArn = s"rn:aws:iam::$arnNumber:role/service-role/AmazonComprehendServiceRole-ParseResumee"

 use val text =  for parsing
