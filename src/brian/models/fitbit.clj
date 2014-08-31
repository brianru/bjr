(ns brian.models.fitbit
  (:require [clj-http.client :as http]
            [oauth.client :as oauth]))

(comment

(def consumer
  (oauth/make-consumer "f203963cdb5d4c07bbf0bd2003d2ccd2"
                       "6bac44594edf4479a17083f6a6989569"
                       "https://api.fitbit.com/oauth/request_token"
                       "https://api.fitbit.com/oauth/access_token"
                       "https://www.fitbit.com/oauth/authorize"
                       :hmac-sha1))
(print consumer)

(def request-token (oauth/request-token consumer))

(print request-token)

(print (oauth/user-approval-uri consumer (:oauth-token request-token)))

;; "http://www.brianjamesrubinton.com/callback/fitbit?
;;  oauth_token=11166c7d41f1b4e925febcc9a82ddb84&
;;  oauth_verifier=9qijne5ar38m5pd738ljruv73c"

(def access-token-response
  (oauth/access-token consumer
                      request-token
                      "724f972504434da243bc04cc85e33201"))

(print access-token-response)

(def credentials
  (oauth/credentials consumer
                     (:oauth_token access-token-response)
                     (:oauth_token_secret access-token-response)
                     :GET
                     "http://api.fitbit.com/1/user/-/activities/steps/date/today/7d.json"))

  (print credentials)

  (def resp (http/post "http://api.fitbit.com/1/user/-/activities/steps/date/today/7d.json" credentials))
)
