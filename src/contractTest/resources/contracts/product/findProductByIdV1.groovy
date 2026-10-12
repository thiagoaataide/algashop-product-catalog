package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept'application/json'
        }
        url("/api/v1/products/e021ed5e-324d-4322-ae47-c46c95382628")
    }

    response {
        status 200
        headers {
            contentType 'application/json'
        }
        body([
                id: fromRequest().path(3),
                addedAt: anyIso8601WithOffset(),
                name: "Notebook X11",
                brand: "Deep Diver",
                regularPrice: 1500.00,
                salePrice: 1000.00,
                inStock: true,
                enabled: true,
                category: [
                        id: anyUuid(),
                        name: "Notebooks"
                ],
                description: "A Gamer Notebook"
        ])
    }
}