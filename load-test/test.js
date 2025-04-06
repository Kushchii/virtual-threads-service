import http from 'k6/http';
import { check, sleep } from 'k6';
import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
import {Rate, Counter } from 'k6/metrics';

// Додаємо додаткові метрики
export let failedRequests = new Rate('http_req_failed');
export let successRequests = new Rate('http_req_success');
export let totalRequests = new Counter('http_reqs_total');
export let statusCodes = new Counter('status_codes');

export let options = {
    vus: 4000,
    duration: '200s',
    thresholds: {
        http_req_duration: ['p(95)<5000'],
        http_req_failed: ['rate<0.01'],
    }
};

export default function () {
    let payload = JSON.stringify({
        id: uuidv4(),
        status: "PENDING",
        userId: "user-123",
        amount: 100.50,
        currency: "USD",
        description: "Test transaction"
    });

    let params = {
        headers: { 'Content-Type': 'application/json' }
    };

    let res = http.post('http://localhost:8080/api/transactions', payload, params);

    totalRequests.add(1);
    statusCodes.add(res.status);

    // Записуємо успішні та невдалі запити
    successRequests.add(res.status === 200);
    failedRequests.add(res.status !== 200);

    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 5000ms': (r) => r.timings.duration < 2500,
    });

    sleep(1);
}