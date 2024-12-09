import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsComponentComponent } from './topics-component.component';

describe('TopicsComponentComponent', () => {
  let component: TopicsComponentComponent;
  let fixture: ComponentFixture<TopicsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopicsComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
