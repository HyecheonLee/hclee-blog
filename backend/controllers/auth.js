const User = require('../models/user')
const shortId = require('shortid')
const jwt = require('jsonwebtoken')
const expressJwt = require('express-jwt')

exports.signup = (req, res) => {
  const {name, email, password} = req.body
  User.findOne({email: email}).exec((err, user) => {
    if (user) {
      return res.status(400).json({
        error: 'Email is taken'
      });
    }

    let username = shortId.generate();
    let profile = `${process.env.CLIENT_URL}/profile/${username}`;

    let newUser = new User({name, email, password, profile, username});
    newUser.save((err, success) => {
      if (err) {
        return res.status(400).json({
          error: err
        });
      }
      // res.json({
      //     user: success
      // });
      res.json({
        message: 'Signup success! Please signin.'
      });
    });
  });
};
exports.signin = (req, res) => {
  const {email, password} = req.body
  //check if user exists
  User.findOne({email}).exec((err, user) => {
    if (err || !user) {
      return res.status(400).json({
        error: "User with that email does not exist. Pleas signup."
      });
    }
    //authenticate
    if (!user.authenticate(password)) {
      return res.status(400).json({
        error: "Email and password do not match."
      });
    }
    //generate a token and send to client
    const token = jwt.sign({_id: user._id}, process.env.JWT_SECRET,
        {expiredIn: "id"});
    res.cookie('token', token, {expiresIn: "id"})
    const {_id, username, name, email, role} = user
    return res.json({
      token, user
    })
  });
}