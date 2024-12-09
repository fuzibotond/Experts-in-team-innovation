import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicRegistrationComponent } from './topic-registration.component';

describe('TopicRegistrationComponent', () => {
  let component: TopicRegistrationComponent;
  let fixture: ComponentFixture<TopicRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopicRegistrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
