package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/categories/9676e7f6-1080-40fd-b487-0ca92dacfb83")
    }
    response {
        status 204
    }
}
