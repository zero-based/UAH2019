const functions = require("firebase-functions");
const unicodeRange = require("unicode-range");
const { parse, tldExists } = require('tldjs');
const { getDomain } = require('tldjs');
const punycode = require('punycode');

exports.emailValidation = functions.https.onRequest((request, response) => {

  var regex = /.+@.+\..{2,}/
  var exception = /[^$!%^&*()=+<>,;|\s]/g

  var email = `${request.query.email}`
  if (email === "" || email === "undefined") {
    response.json({ message: "Provide email address!" })
    return
  }

  var rawEmail = email.replace(/[@_.-]/g, "");

  var getRange = c => unicodeRange(`U+${c.charCodeAt(0).toString(16)}`);
  var range = getRange(rawEmail);
  var isConsistent = ![...rawEmail].some(c => getRange(c) !== range);
  var isEmail = regex.test(email) && exception.test(email)
  var isTldExists = tldExists(email)
  var isEmailValid = isConsistent && isEmail && isTldExists
  var isDomainValid = isDomainValid(email)

  response.json({
    email,
    range: isConsistent ? range : "MIXED",
    isConsistent,
    isEmail,
    isTldExists,
    isEmailValid,
    isDomainValid
  });

});

function isDomainValid(validating){
  var isDomainLengthValid = true
  var isTldLengthValid = true
  var domain = getDomain(email)
  var tldName  = domain.split(".")[1]
  var domainLength
  var tldLength 

  if(range !== "English"){
    domainLength = (punycode.encode(domain)).length + 4
    tldLength = (punycode.encode(tldName)).length + 4
  }
  else {
    domainLength = domain.length
    tldLength = tldName.length
  }

  if(domainLength > 255) {
  isDomainLengthValid === false
  }
  if(tldLength > 63){
    isTldLengthValid === false
  }  

  return isDomainLengthValid && isTldLengthValid

}