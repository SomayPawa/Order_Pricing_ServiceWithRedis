import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 5000,
    duration: '30s',
};

export default function () {

    const id = Math.floor(Math.random() * 1100000) + 1;

    const url = `http://localhost:8080/api/v1/products/${id}`;

    const params = {
        tags: {
            name: 'GET /api/v1/products/:id',
        },
    };

    const response = http.get(url, params);

    check(response, {
        'status is 200': (r) => r.status === 200,
    });
}