package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private String DYNAMODB_TABLE_NAME = "newTable";
    private AmazonDynamoDB dynamoDbClient = AmazonDynamoDBClientBuilder.standard().defaultClient();
    ScanRequest scanRequest = new ScanRequest()
            .withTableName(DYNAMODB_TABLE_NAME)
            .withLimit(100);
    ScanResult scanResult = dynamoDbClient.scan(scanRequest);

    @SneakyThrows
    public APIGatewayProxyResponseEvent handleRequest
            (final APIGatewayProxyRequestEvent input, final Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("Loading Java Lambda handler of Proxy");
        logger.log(String.valueOf(input.getBody().getBytes().length));


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        ObjectMapper objectMapper = new ObjectMapper();
        Info info = objectMapper.readValue(input.getBody(), Info.class);
        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
        Table table = dynamoDB.getTable("newTable");
        //GetItemSpec spec = new GetItemSpec().withPrimaryKey("s3Url", info.setS3Url("s3Url"));

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("id", info.id)
                .withUpdateExpression("set " +
                        "FirstName=:FirstName, LastName=:LastName, Policy=:Policy")
                .withValueMap(new ValueMap().withString(":FirstName", info.FirstName)
                        .withString(":LastName", info.LastName)
                        .withString(":Policy", info.policy))
                .withReturnValues(ReturnValue.UPDATED_NEW);

//        withKeyComponent("s3Url", info.s3Url).withString("id", info
//        .id).withString("FirstName", info.FirstName).withString("LastName", info.LastName);
////                .withString("id", info.id)
//                .withString("FirstName", info.FirstName)
//                .withString("LastName", info.LastName);
        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: ");
            System.err.println(e.getMessage());
        }
//        table.putItem(updateItemSpec);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("User ID:" + info.id);


    }

//    private String getPageContents(String address) throws IOException{
//        URL url = new URL(address);
//        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
//            return br.lines().collect(Collectors.joining(System.lineSeparator()));
//        }
//    }
}
