import http from 'k6/http';
import { check } from 'k6';
import exec from 'k6/execution';

export const options = {
    vus: 10,
    iterations: 1000000,
};

export default function () {

    const productNumber = exec.scenario.iterationInTest + 1;

    const payload = JSON.stringify({
        name: `Product ${productNumber}`,
        description: `Test product ${productNumber}`,
        price: 100 + productNumber,
        category: `Category ${((productNumber - 1) % 10) + 1}`,
        stock: 100
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(
        'http://localhost:8080/api/v1/products',
        payload,
        params
    );

    check(response, {
        'status is 201': (r) => r.status === 201,
    });
}