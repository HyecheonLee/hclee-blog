import Document, {Head, Html, Main, NextScript} from 'next/document'

class MyDocument extends Document {
  render() {
    return (
        <Html lang="ko">
          <Head>
            <meta charSet={"UTF-8"}/>
            <meta name="viewport"
                  content="width=device-width, initial-scale=1.0"/>
                  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"/>
          </Head>
          <body>
          <Main/>
          <NextScript/>
          </body>
        </Html>
    )
  }
}

export default MyDocument