export type AmplifyDependentResourcesAttributes = {
    "api": {
        "taskmasterApi": {
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    },
    "auth": {
        "taskmaster8498adec": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string",
            "CreatedSNSRole": "string"
        }
    },
    "storage": {
        "taskmaster": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "analytics": {
        "taskmasterPinPoint": {
            "Region": "string",
            "Id": "string",
            "appName": "string"
        }
    }
}