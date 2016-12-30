# EBANX SDK

## Intro

The EBANX Android SDK was created to facilitate the creation of tokens, set the CVV created a token and tokens list already created by the SDK.

Other features need to be made from server to server using its integration key.

## Features

- [Create Token](#create-token)
- [Set CVV](#set-cvv)
- [Get all tokens](#get-all-tokens)
- [Get token by masked creditcard number](#get-token-by-masked-creditcard-number)
- [Delete token](#delete-token)
- [Delete all tokens](delete-all-tokens)

<!-- - [Complete Documentation](https://www.ebanx.com/business/en/developers/sdk-reference/android-sdk-reference) -->
    
## Requirements

- Android 4.0.3+

## Integration

#### Download

Download [the latest aar](https://bintray.com/ebanx/maven/download_file?file_path=com%2Febanx%2Fsdk%2F1.0.1%2Fsdk-1.0.1.aar)

#### Gradle

```groovy
compile 'com.ebanx:sdk:1.0.1'
```

#### Maven

```xml
<dependency>
  <groupId>com.ebanx</groupId>
  <artifactId>sdk</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

## Configuration

#### How to get a Public Key

Public key and the integration of key are generated at the time of creation of the merchant account.

[Click here for more details](https://www.ebanx.com/business/en)


#### Set Public Key

```java
// Configuration for production environment
EBANX.configure(getApplicationContext(), "your public key");

// Configuration for development environment
EBANX.configure(getApplicationContext(), "your public key", true);
```

## Usage

#### Create Token

The token operation is used to create a token for a given credit card to be used for recurrent payments.

```java
// Create a creditcard
EBANXCreditCard card = new EBANXCreditCard("Fulano de tal", "4012888888881881", "12/2016", "321", EBANXCreditCardType.Visa);

EBANX.Token.create(card, EBANXCountry.BR, new EBANXTokenRequestComplete() {
    @Override
    public void Success(EBANXToken token) {
        // Object EBANXToken
    }

    @Override
    public void APIError(EBANXError error) {
        // Object EBANXError
        // possibles type values:
        // PublicKeyNotSet - Public key is not set in EBANX.configure()
        // InvalidPublicKey - Public key invalid (API response)
        // ParseError - Object not found
        // GenericError
    }

    @Override
    public void NetworkError(Exception e) {
        // Object error from NSURLSession case request fail
    }
});
```

#### Set CVV

The setCVV operation is used to temporary associate a CVV with an existing token. This CVV will be used by the next request made with the associated token and then discarded.

The setCVV operation is useful for one-click payments, where you already have the customer's credit card information and cannot send the CVV from your server.

```java
// Set CVV from credicard token
EBANXToken token = new EBANXToken("123456......123456", "4111********1111");

EBANX.Token.setCVV(token, "123", new EBANXTokenRequestComplete() {
    @Override
    public void Success(EBANXToken token) {
        // Object EBANXToken
    }

    @Override
    public void APIError(EBANXError error) {
        // Object EBANXError
        // possibles type values:
        // PublicKeyNotSet - Public key is not set in EBANX.configure()
        // InvalidPublicKey - Public key invalid (API response)
        // ParseError - Object not found
        // GenericError
    }

    @Override
    public void NetworkError(Exception e) {
        // Object error from NSURLSession case request fail
    }
});
```

#### Get all tokens

```java
// return List<EBANXToken>
List<EBANXToken> tokenList = EBANX.Token.getTokens();
```

#### Get token by masked creditcard number

```java
// return EBANXToken
EBANXToken token = EBANX.Token.getToken("4111********1111");
```

#### Delete token

```java
EBANToken currentToken = .......

EBANX.Token.deleteToken(token);
```

#### Delete all tokens

```java
EBANX.Token.deleteAllTokens();
```

## Credit

EBANX SDK is owned and maintained by the [EBANX](https://www.ebanx.com/br)

Contact email [mobile@ebanx.com](mailto:mobile@ebanx.com)

## License

EBANX SDK is released under the MIT license. See LICENSE for details.
