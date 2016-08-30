# EBANX SDK

## Intro

The EBANX Android SDK was created to facilitate the creation of tokens, set the CVV created a token and tokens list already created by the SDK.

Other features need to be made from server to server using its integration key.

[SDK integration examples for the server](#integration-examples)

## Features

- [Create Token](#create-token)
- [Set CVV](#set-cvv)
- [Get all tokens](#get-all-tokens)
- [Get token by masked creditcard number](#get-token-by-masked-creditcard-number)
- [Delete token](#delete-token)
- [Delete all tokens](delete-all-tokens)
- [Complete Documentation](http://cocoadocs.org/docsets/EBANX)
    
## Requirements

- Android 4.0.3+

## Integration

- github
- gradle

## Configuration

### How to get a Public Key

Public key and the integration of key are generated at the time of creation of the merchant account.

[Click here for more details](https://www.ebanx.com/business/en)

<!--
### Set Public Key

```swift
// import EBANX module in AppDelegate
import EBANX

// Configure public key SDK in application:didFinishLaunchingWithOptions:
func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
    // Configuration for production environment
    EBANX.configure("your public key")
    
    // Configuration for development environment
    EBANX.configure("your public key", testMode: true)
    return true
}
```

## Usage

### Create Token

The token operation is used to create a token for a given credit card to be used for recurrent payments.

```swift
//import module
import EBANX

// Create a creditcard
let card = EBANXCreditCard(name: "Fulano de tal", number: "4111111111111111", dueDate: "12/2015", cvv: "123", type: EBANXCreditCardType.Visa)

EBANX.Token.create(card, country: EBANXCountryType.BR) { (result: EBANXTokenResult) in
    switch result {
    case .Success(let token):
        // Object EBANXToken
    case .APIError(let apiError):
        // enum EBANXAPIErrors
        // possibles values:
        // PublicKeyNotSet - Public key is not set in EBANX.configure()
        // InvalidPublicKey - Public key invalid (API response)
        // ParseError - Object not found
        // ResponseError - Object EBANXError
    case .NetworkError(let error):
        // Object error from NSURLSession case request fail
    }
}
```

### Set CVV

The setCVV operation is used to temporary associate a CVV with an existing token. This CVV will be used by the next request made with the associated token and then discarded.

The setCVV operation is useful for one-click payments, where you already have the customer's credit card information and cannot send the CVV from your server.

```swift
//import module
import EBANX

// Set CVV from credicard token
EBANX.Token.setCVV(EBANXToken(token: "123456......123456", cardNumberMasked: "4111********1111"), cvv: "123") { (result: EBANXTokenResult) in 
    switch result {
    case .Success(let token):
        // Object EBANXToken
    case .APIError(let apiError):
        // enum EBANXAPIErrors
        // possibles values:
        // PublicKeyNotSet - Public key is not set in EBANX.configure()
        // InvalidPublicKey - Public key invalid (API response)
        // ParseError - Object not found
        // ResponseError - Object EBANXError
    case .NetworkError(let error):
        // Object error from NSURLSession case request fail
    }
}
```

### Get all tokens

```swift
// return Array<EBANXToken>
let tokens = EBANX.Token.getTokens()
```

### Get token by masked creditcard number

```swift
// return EBANXToken?
let currentToken = EBANX.Token.getToken("4111********1111")
```

### Delete token

```swift
let currentToken = .......

EBANX.Token.deleteToken(currentToken)
```

### Delete all tokens

```swift
EBANX.Token.deleteAllTokens()
```

### Integration Examples

- TODO

## Credit

EBANX SDK is owned and maintained by the [EBANX](https://www.ebanx.com/br)

Contact email [mobile@ebanx.com](mailto:mobile@ebanx.com)

## License

EBANX SDK is released under the MIT license. See LICENSE for details.
-->
