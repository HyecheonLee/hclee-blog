const Tag = require('../models/tag')
const slugify = require('slugify')
const {errorHandler} = require('../helpers/dbErrorHandler')

exports.create = (req, res) => {
  const {name} = req.body
  let slug = slugify(name).toLowerCase()
  const tag = new Tag({name, slug})

  tag.save((err, data) => {
    if (err) {
      return res.status(400).json({
        error: errorHandler(err)
      });
    }
    res.json(data)
  });
};

exports.list = (req, res) => {
  Tag.find({}).exec((err, data) => {
    if (err) {
      return res.status(400).json({
        error: errorHandler(err)
      })
    }
    res.json(data);
  })
}

exports.read = (req, res) => {
  const slug = req.params.slug.toLowerCase()
  Tag.findOne({slug}).exec((err, category) => {
    if (err) {
      return res.status(400).json({
        error: errorHandler(err)
      })
    }
    res.json(category);
  });
}

exports.remove = (req, res) => {
  const slug = req.params.slug.toLowerCase()
  Category.findOneAndRemove({slug}).exec((err, data) => {
    if (err) {
      return res.status(400).json({
        error: errorHandler(err)
      })
    }
    res.json({
      message: "Category deleted success"
    });
  })
}