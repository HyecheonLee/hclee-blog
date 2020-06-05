const express = require('express')
const router = express.Router()
const {signup, signin} = require("../controllers/auth")

const {runValidation} = require('../validators')
const {userSignupValidator} = require('../validators/auth')

router.post('/signup', userSignupValidator, runValidation, signup);
router.post("/signin", userSignupValidator, runValidation, signin);

module.exports = router