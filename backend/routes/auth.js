const express = require('express')
const router = express.Router()
const {signup, signin, signout, requireSignin} = require("../controllers/auth")

const {runValidation} = require('../validators')
const {userSignupValidator} = require('../validators/auth')

router.post('/signup', userSignupValidator, runValidation, signup);
router.post("/signin", userSignupValidator, runValidation, signin);
router.get("/signout", signout);
//test
router.get("/secret", requireSignin, (require, res) => {
  res.json({
    message: "you have access to secret page"
  })
});

module.exports = router