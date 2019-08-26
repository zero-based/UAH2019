const functions = require("firebase-functions");
const unicodeRange = require("unicode-range");

exports.emailValidation = functions.https.onRequest((request, response) => {

  var email = request.query.email.toString();

  var getRange = c => unicodeRange("U+" + c.charCodeAt(0).toString(16));
  var range = getRange(email);
  var isConsistent = ![...email].some(c => getRange(c) !== range);

  var rangesRTL = [
    "Arabic",
    "Imperial Aramaic",
    "Old Turkic",
    "Hebrew",
    "Old Persian"
  ];
  var isRTL = rangesRTL.some(r => r === range)

  response.json({
    range: isConsistent ? range : "MIXED",
    isRTL: isConsistent ? isRTL : "MIXED",
    isConsistent
  });

});