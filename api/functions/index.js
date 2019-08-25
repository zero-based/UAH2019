const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
 exports.emailValidation = functions.https.onRequest((request, response) => {
    response.status(200).json({
        message: 'It worked!'
    })
   });