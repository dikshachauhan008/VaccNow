package com.xebia.vaccnow.service;

import com.xebia.vaccnow.repository.BranchRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;
    @InjectMocks
    private BranchService branchService;



}