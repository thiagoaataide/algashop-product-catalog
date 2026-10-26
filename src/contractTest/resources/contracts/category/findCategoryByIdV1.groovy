package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept 'application/json'
        }
        url("/api/v1/categories/e021ed5e-324d-4322-ae47-c46c95382628")
    }

    response {
        status 200
        headers {
            contentType 'application/json'
        }
        body([
                id     : fromRequest().path(3),
                name   : "Notebooks",
                enabled: true
        ])
    }
}
