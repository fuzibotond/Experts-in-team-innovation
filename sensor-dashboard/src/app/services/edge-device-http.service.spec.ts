import { TestBed } from '@angular/core/testing';

import { EdgeDeviceHttpService } from './edge-device-http.service';

describe('TopicApiService', () => {
  let service: EdgeDeviceHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EdgeDeviceHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
