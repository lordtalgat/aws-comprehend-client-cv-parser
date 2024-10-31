import com.amazonaws.auth.{AWSCredentialsProvider, AWSCredentialsProviderChain, AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.internal.StaticCredentialsProvider
import com.amazonaws.services.comprehend.AmazonComprehend
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder
import com.amazonaws.services.comprehend.model.{BatchDetectEntitiesRequest, DetectEntitiesRequest, DetectEntitiesResult}
import com.amazonaws.services.s3.AmazonS3ClientBuilder

object Main {
  private val awsCreds = new BasicAWSCredentials("username", "password")
  private val providers: AWSCredentialsProvider = new StaticCredentialsProvider(awsCreds)

  def main(args: Array[String]) = {
    testText
  }

  def textEntities() = {
    val text = "Regina Saskatchewan\n Mobile: +7(306) 370-5599\n lacey.unrau@hotmail.com\n ca.linkedin.com/in/laceyunrau\n LACEY UNRAU, BHLR, CPHR\n"

    val comprehendClient = AmazonComprehendClientBuilder.standard.withCredentials(providers).withRegion("us-east-1").build

    // Call detectEntities API
    println("Calling DetectEntities")
    val detectEntitiesRequest = new DetectEntitiesRequest().withText(text).withLanguageCode("en")
    val detectEntitiesResult = comprehendClient.detectEntities(detectEntitiesRequest)
    detectEntitiesResult.getEntities.forEach(println)
    println("End of DetectEntities\n")
  }

  def realtime() = {
    val comprehendClient =
      AmazonComprehendClientBuilder.standard
        .withCredentials(providers)
        .withRegion("us-east-1")
        .build()

    import com.amazonaws.services.comprehend.model.InputFormat
    val inputS3Uri = "s3://input bucket/input path"
    val inputDocFormat = InputFormat.ONE_DOC_PER_FILE
    val outputS3Uri = "s3://output bucket/output path"
    val arnNumber = "000000000000"
    val dataAccessRoleArn = s"rn:aws:iam::$arnNumber:role/service-role/AmazonComprehendServiceRole-ParseResumee"
    val numberOfTopics = 10

    import com.amazonaws.services.comprehend.model.InputDataConfig
    import com.amazonaws.services.comprehend.model.OutputDataConfig
    import com.amazonaws.services.comprehend.model.StartTopicsDetectionJobRequest
    val startTopicsDetectionJobRequest = new StartTopicsDetectionJobRequest()
      .withInputDataConfig(new InputDataConfig().withS3Uri(inputS3Uri).withInputFormat(inputDocFormat))
      .withOutputDataConfig(new OutputDataConfig().withS3Uri(outputS3Uri))
      .withDataAccessRoleArn(dataAccessRoleArn)
      .withNumberOfTopics(numberOfTopics)

    import com.amazonaws.services.comprehend.model.DescribeTopicsDetectionJobRequest
    import com.amazonaws.services.comprehend.model.DescribeTopicsDetectionJobResult
    import com.amazonaws.services.comprehend.model.ListTopicsDetectionJobsRequest
    import com.amazonaws.services.comprehend.model.ListTopicsDetectionJobsResult
    import com.amazonaws.services.comprehend.model.StartTopicsDetectionJobResult
    val startTopicsDetectionJobResult = comprehendClient.startTopicsDetectionJob(startTopicsDetectionJobRequest)

    val jobId = startTopicsDetectionJobResult.getJobId
    println("JobId: " + jobId)

    val describeTopicsDetectionJobRequest = new DescribeTopicsDetectionJobRequest().withJobId(jobId)

    val describeTopicsDetectionJobResult = comprehendClient.describeTopicsDetectionJob(describeTopicsDetectionJobRequest)
    println("describeTopicsDetectionJobResult: " + describeTopicsDetectionJobResult)

    val listTopicsDetectionJobsResult = comprehendClient.listTopicsDetectionJobs(new ListTopicsDetectionJobsRequest)
    println("listTopicsDetectionJobsResult: " + listTopicsDetectionJobsResult)

  }

  def testText() = {
    val txt = "Lacey Unrau, BHRLR, cphr\n\n5\n\nLACEY UNRAU, BHLR, cphr\nRegina Saskatchewan\nMobile: (306) 370-5599\nlacey.unrau@hotmail.com \nca.linkedin.com/in/laceyunrau\nProfile\n\nA Human Resource Professional with over 17 year’s progressive experience in diverse industries of Construction, Mining and Engineering operating at a local, regional, and global scale. Thrives on new challenges; adapts and embraces change, committed to continuous learning and professional growth. A Generalist Business Partner with demonstrated ability to exercise confidence and discretion, proficient in all HR competencies and functional areas, with a passion for collaboration, knowledge sharing regarding best.\nCORE COMPETENCIES INCLUDE:\n\n\t· Employee & Labour Relations \n\n· Compliance \n· Policy and Program development\n\t· Workplace investigations \n\n· Leaves management & return to work programs\n\n\t· Inclusion, Diversity and Equity\n\n· Total Rewards\n\n· Performance & Talent Management\n\t· Strategic Workforce Planning\n· Organizational Development & Talent Segmentation \n\n\n Experience\n\nDirector, Human Resources\n\n\n\n\n\n\n2019- present\nDes Nedhe Group of Companies\n\n\n\n\n\n\n\n\nOwned by English River First Nation, Des Nedhe Group owns and operates a diverse collection of Ingenious-owned businesses operating in construction and mining, roadways, retail, marketing, and professional service industries. The Director of Human Resources is responsible for the development and management of employee and labour relations, talent management, total rewards, policy, and program development and managing the team of individuals supporting the business units.  \n· Program and Policy development: Rewrote or created company policy and programs such as Code of Conduct, Inclusion, Diversity and Equity, Recruitment, Early and Safe Return to Work, Leave, Progressive Discipline, COVID-19, Respect in the workplace, Fatigue Management, Excessive hours Compensation and Total Reward Programs.\n\n· Training and Development: Created and facilitated training programs including Organizational Goal setting, Performance Driven Culture, Talent Segmentation, Performance Appraisals.\n· Collaborating with Operations and Legal department to develop strategies and navigate the labour relations landscapes within Saskatchewan and Ontario.\n· Created or improved talent acquisition, engagement strategies and demographic reporting to ensure maintenance of 40% Indigenous participation across the group. \n\n· Total Rewards: Created and implemented job classification and total reward approach relevant to each entities managerial strategy and finalizing the compensation and short-term-incentive program utilizing multi-goal, multi-company management by objectives approach. \n· Talent: Developed workflows and processes pertaining to talent acquisition and onboarding from Executives, managers, employees (union and non-union), recruitment strategies, career fair participation and summer student/intern program, funding acquisition, orientation, and onboarding.\n· Researched, selected, and currently finalizing the configuration and implementation of a CriterionHCM HRMS for the group of companies to meet each entities unique needs. Partnering with Payroll, undertook and leading the configuration and implementation of a Human Resource Management System resulting in greater communication, transparency, efficiencies, and accessibility. \nSr. Human Resource Consultant\n\nStantec Consulting Limited\n\n\n\n\n\n\n\n2013- 2019\nPartnering with various leaders and organizational groups in a collaborative matrix organization to support regional business needs. Leverage broad employee relations knowledge to optimize management and handle employee relations.    \n· Establish and maintain effective working relationships with operational and practice groups, including employees, creating solutions regarding a variety of HR matters to meet needs\n· Actively contribute to the delivery, communication, and measurement of short- and long-term business strategies and plans in alignment with the broader delivery of HR services, programs, and functions.\n· Successfully partner with Leadership to strategically develop and execute people management actions including communication, engagement, talent development, change management, compensation and classification practices, succession planning and culture.\n· Provide strategic guidance on HR related issues including compliance, policies, compensation, benefits, retention, performance management and employee engagement.\n· Coordinate and facilitate training sessions on various HR initiatives (career development, performance management, interpersonal communication, compensation philosophy and other corporate programs and initiatives).\n· Partner with the broader HR community to assess, leverage and execute integrated HR solutions.\n· Proactively lead and articulated requirements on complex practice areas related to provincial legislation and CRA compliance (overtime, hours of work, days of rest, gift giving, vehicle use).\n\n· Lead and managed both the regional immigration and permanent residency program\n\n· Established best practice to maximize and leverage provincial and federal funding programs, securing over $150,000 in grants for training or student hiring.  \n\n· Working with HSSE and Leaves management, improved and mapped North American injury, illness leaves decision tree and return to work program. \nHuman Resource Business Partner \n\nVale Potash Canada – Regina, SK\n\n\n\n\n\n\n2012 – 2012\n\nWork directly with the Brazilian and Canadian Business Managers and Project Leaders to develop a $4 Billion Potash Project, brokering Human Resource services between the business and HR process owner teams becoming a business ally, a driver and facilitator of change – translating business needs into Human Resource requirements. \n· Created employee orientation program for 3 phases of the project (feasibility, construction, and operations)\n\n· Produced and managed a $20 million-dollar department budget that included: compensation; recruitment, promotion, global mobility, relocation, and training costs for whole project team\n· Created from ground up a project owner specific safety training and employee personal training program.\n· Developed with Project Director, the organizational structure and aggressive mobilization strategy to acquire 57 professionals for the project team by the end of 2012, and 73 by mid 2013. Created project job descriptions and scope, developed recruitment strategy for Western Canada\n· Managed project recruitment initiatives, including international and national contractors, hiring managers and Canadian recruitment team. \n· Managed all hiring activities for the project team, participated in interviews, provided recruitment tools, short listed candidates, conducted interviews for supervision and management positions.\n· Collaborated with Sustainability Department and Project Director to create First Nations and Community Engagement Strategies.\n· Participated in program rollout and provided guidance for Career & Succession Planning, Annual Incentive Programs, Work Force Planning.\n· Interpreted reports and trends, communicate suggestions and mitigation strategies to management.\nHuman Resource Manager\n\nLoblaw – Regina, SK\n\n\n\n\n\n\n\n2011 – 2012 \n\nWas the Human Resource manager for four (4) stores in southern Saskatchewan, represented by two (2) separate collective agreements representing approximately 1,200 employees. Created, supported, and implemented corporate change initiatives, worked with managers and supervisors to increase engagement, morale productivity and reduce attrition. \n\n· Increased store engagement standing to be in the top five (5), instead bottom rating.\n\n· Informed managers of legal obligations such as duty to accommodate, modified duties, immigration and TFW programs in addition to labour and employment law requirements\n· Facilitated gradual return to work or modified duties programs for employees and managers\n\n· Facilitated mediation and conflict resolution between managers, union stewards and employees\n· Conducted investigations, provided solutions, authorized, and participated in employee disciplines and terminations\n\n· Participated in grievance and discipline meetings, seeking resolution\n· Managed immigration issues, employee work permits and work visa’s \n\nHuman Resource Generalist\n\n\n\n\n\n 2006 – 2009; 2011\nLedcor Group of Companies – Fort McMurray AB\n\n\n\n\n\n\n\nPartnered with construction, project, and contract management teams to meet contractual obligations and overcome site specific challenges to meet project demands and deadlines while working within the restrains of both corporate and client needs.  I was a Human Resource Generalist on three (3) large industrial projects ranging in scope, size, and manpower of 550 employees – 1,700 employees. (Kearl Oil Sands, Shell Albian, and the Opti-Nexen Long Lake Projects) \n\n\n\n\n· Created and implemented a pay equity policy and procedure that was adopted nationally, eliminating exposure and violations to Employment Law, Trades and Apprenticeship Act, and non-compliance to collective agreements. The policy created structure and consistency for proper wage classification and job progression of employees. \n· Developed various HR policies and procedures, identified project HR roles, established processes for: payroll, recruitment, travel, safety, and training.\n· Managed a variety of Human Resource Information systems, consolidated training records from 9 project sites to create a multi-site training matrix and training management program, resulting in more effective and efficient training needs analysis.\n· Employee & Labour Relations resolving challenges, conflicts, grievances.\n· Managed, coordinated, and facilitated: orientations, information sessions, Apprentice and Journeyman Rights and Responsibility program, wage classification and progression, company benefits.\n· Compensation & Benefits, authorized promotions, data changes and job progression \n· Administration of multiple Collective Agreements (a minimum one (1) to maximum of four (4) per project) ensuring adherence, consistency, and compliance.\n· Conducted investigations, resulting in coaching, progressive discipline or termination of supervision and union employees.\n· Conducted various audits, proposed solutions and risk mitigation to management\n\n· Responsible for managing recruitment and manpower loading of the projects, worked closely with hiring managers and recruitment teams to ensure needs were met.\n· Primary liaison and company representative with multiple apprenticeship boards across Canada \n\nHuman Resources Officer\n\nSaskatchewan Indian Gaming Authority – PHC Yorkton, SK          \n   2010 - 2011           \n\nSIGA is a non-profit organization with revenues more than 60M, I was the Human Resource officer responsible for all facets of Human Resources for four (4) departments with approximately 200 employees, often assisting other HRO’s and managers in the organization.\n· Created with HR manager and team, an Attendance Awareness Program (incorporating duty to accommodate and coaching and discipline program), reducing lost time, lates, and no-shows by 63%\n\n· Managed graduated and modified return to work programs, educated managers on duty to accommodate, reducing lost time and expedited employee’s return to work\n\n· Conducted numerous workplace investigations, coaching and discipline sessions, facilitated mediations between managers and employees\n· Managed training matrix, increased managers buy-in and employee participation in training programs, obtaining 90%-100% department completion \n\n· Drafted and implemented last chance agreements, employee terminations and disciplines\n· Learning & Development - facilitation of training to groups of various sizes \n\n· Partnered with OHS/WCB/EI/Parkland College/ SLGA on numerous initiatives \n· Managed recruitment & staffing, maintaining transparency with protected peoples’ exemption. \n· Revised existing data management & various HRIS systems and reports, creating efficiencies while showcasing the positive impact Human Resource Management had on the organization \nOffice Manager\nAMEC AMERICAS – Saskatoon SK \n                                                                                2009 - 2010\nAMEC AMERICA was the main EPCM on the PCS Cory Expansion Project, responsible for overseeing multiple contractors in all areas of construction and commissioning. The contract was valued at approximately $ 1.3B and managed more than 500 direct and 1,200 indirect employees. \n· Responsible for project logistics, new hires, terminations, office spaces, materials  \n\n\n· Worked with Project management on project de-mobilization of assets and personnel \n\n· Integrating new employees and contractors onto the project\n\n· Supervision, coaching and mentorship of project administrators\n\n· Created and implemented document control system to manage RFI’s, Change Notices, deficiencies\n· Amended various processes creating efficiencies and eliminating redundancies \n\n· Arrangements of new hires & site terminations\nEducation & Associations\n· Bachelor of Human Resource and Labour Relations, Athabasca University 2020 (Honors)  \n\n· CPHR Designation, Canadian Council of Human Resource Professionals, 2010\n\n· Diploma, Human Resource Management, Keyano College, 2007\n\n· High School Diploma, Bonnyville Centralized High School, 2002\n· Member, Saskatchewan Chartered Human Resource Professional  (CPHR) 2009- present\n\nInterests & Hobbies\n\n\t· Spending time with family and friends\n· Gardening (flower & vegetable) and canning \n· Reading  \n\t· Travelling \n\n· Exercising/weightlifting\n\n· Natural science, astrophysics\n\n\nReferences available upon request"
    val split = txt.split("\n").map(_.trim).filter(e => e.nonEmpty && e.length>4)
    val txtAfter = split.mkString("\n")
    println(txtAfter)
  }
}